<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="https://cdn.bootcss.com/jquery.fileDownload/1.4.2/jquery.fileDownload.min.js"></script>
</head>
<body>
<a href="#" download="aaa.txt"></a>
</body>
<script>
    var downloadURL = "TestHandler.ashx";
    $.fileDownload(downloadURL, {
        httpMethod: 'post',
        data: { 'FileName': 'test.txt' },
        prepareCallback: function (url) {
            console.log("文件下载中...");
            // 数据加载动画
            $("body").append('<div id="Loading" style="background:url(images/load.png) top center no-repeat;"></div>');
        },
        abortCallback: function (url) {
            // 异常终止
            console.log("文件下载异常！！");
            $("#Loading").remove();
        },
        successCallback: function (url) {
            console.log("文件下载成功！！");
            $("#Loading").remove();
        },
        failCallback: function (html, url) {
            console.log("文件下载失败！！");
            $("#Loading").remove();
        }
    });
</script>
</html>