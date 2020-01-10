<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="/layui-v2.5.5/layui/css/layui.css">
    <script src="layui-v2.5.5/layui/layui.js"></script>
</head>
<body>
<#--<div id="uploadImg"></div>
<button class="layui-btn test" lay-data="{url: '/a/'}">上传图片</button>
<button class="layui-btn test" lay-data="{url: '/b/', accept: 'file'}">上传文件</button>-->
<#--<button type="button" class="layui-btn" id="test1">
    <i class="layui-icon">&#xe67c;</i>上传图片
</button>-->
<div class="layui-upload">
    <button type="button" class="layui-btn" id="test1">上传图片</button>
    <div class="layui-upload-list">
        <img style="width: 100px ;height: 100px" class="layui-upload-img" id="demo1">
        <p id="demoText"></p>
    </div>
</div>

<div class="layui-upload">
    <button type="button" class="layui-btn layui-btn-normal" id="testList">选择多文件</button>
    <div class="layui-upload-list">
        <table class="layui-table">
            <thead>
            <tr><th>文件名</th>
                <th>大小</th>
                <th>状态</th>
                <th>操作</th>
            </tr></thead>
            <tbody id="demoList"></tbody>
        </table>
    </div>
    <button type="button" class="layui-btn" id="testListAction">开始上传</button>
</div>
<script>
    layui.use('upload', function(){

        var $ = layui.jquery
                ,upload = layui.upload;
        //执行实例
        var uploadInst = upload.render({
            elem: '#test1' //绑定元素
            ,url: 'file-upload' //上传接口
            ,accept :"images"
            ,field :"fileName"
            ,size :"1024"
            ,multiple :false
            ,number :0
            ,drag :false
            ,acceptMime: 'image/jpg, image/png'

            ,before: function(obj){
                //预读本地文件示例，不支持ie8
                obj.preview(function(index, file, result){
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            ,done: function(res, index, upload){
                //假设code=0代表上传成功
                if(res.code == 0000){
                    return layer.msg('上传成功');
                }else {
                    return layer.msg('上传失败');
                }

                }
            ,error: function(){
                //上传失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
                });
            }

        })
        //var element = layui.element;
        //多文件列表示例
        var demoListView = $('#demoList')
                ,uploadListIns = upload.render({
            elem: '#testList'
            ,url: '/file-upload'
            ,accept: 'file'
            ,multiple: true
            ,auto: false
            ,field :"fileName"
            ,bindAction: '#testListAction'
            /*,progress: function(n){
                var percent = n + '%' ;//获取进度百分比
                element.progress('demo', percent); //可配合 layui 进度条元素使用
            }*/
            ,choose: function(obj){
                var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
                //读取本地文件
                obj.preview(function(index, file, result){
                    var tr = $(['<tr id="upload-'+ index +'">'
                        ,'<td>'+ file.name +'</td>'
                        ,'<td>'+ (file.size/1014).toFixed(1) +'kb</td>'
                        ,'<td>等待上传</td>'
                        ,'<td>'
                        ,'<button class="layui-btn layui-btn-xs demo-reload layui-hide">重传</button>'
                        ,'<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                        ,'</td>'
                        ,'</tr>'].join(''));

                    //单个重传
                    tr.find('.demo-reload').on('click', function(){
                        obj.upload(index, file);
                    });

                    //删除
                    tr.find('.demo-delete').on('click', function(){
                        delete files[index]; //删除对应的文件
                        tr.remove();
                        uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                    });

                    demoListView.append(tr);
                });
            }
            ,done: function(res, index, upload){
                if(res.code == 0000){ //上传成功
                    var tr = demoListView.find('tr#upload-'+ index)
                            ,tds = tr.children();
                    tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                    tds.eq(3).html(""); //清空操作
                    return delete this.files[index]; //删除文件队列已经上传成功的文件
                }
                this.error(index, upload);
            }
            ,error: function(index, upload){
                var tr = demoListView.find('tr#upload-'+ index)
                        ,tds = tr.children();
                tds.eq(2).html('<span style="color: #FF5722;">上传失败</span>');
                tds.eq(3).find('.demo-reload').removeClass('layui-hide'); //显示重传
            }
        });
    });
</script>
</body>
</html>