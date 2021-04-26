<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    /*
    * 根据交易表中不同的阶段的数量进行一个统计，最终形成一个漏斗图（倒三角）
    * 将统计出来的数量比较高的，往上面排列。
    * 将统计出来的数量比较少的，往下面排列。
    * */
%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <script src="workbench/ECharts/echarts.min.js"></script>
    <script src="jquery/jquery-1.11.1-min.js"></script>
    <script>
        $(function (){
           //在页面加载完毕后绘制统计图表
            getCharts();
        });
        function getCharts(){
            $.ajax({
                url: "workbench/transaction/getCharts.do",
                type: "get",
                dataType: "json",
                success: function (data) {

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '交易漏斗图',
                            subtext: '统计交易阶段数量的漏斗图'
                        },
                        series: [
                            {
                                name:'交易漏斗图',
                                type:'funnel',
                                left: '10%',
                                top: 60,
                                //x2: 80,
                                bottom: 60,
                                width: '80%',
                                // height: {totalHeight} - y - y2,
                                min: 0,
                                max: data.total,
                                minSize: '0%',
                                maxSize: '100%',
                                sort: 'descending',
                                gap: 2,
                                label: {
                                    show: true,
                                    position: 'inside'
                                },
                                labelLine: {
                                    length: 10,
                                    lineStyle: {
                                        width: 1,
                                        type: 'solid'
                                    }
                                },
                                itemStyle: {
                                    borderColor: '#fff',
                                    borderWidth: 1
                                },
                                emphasis: {
                                    label: {
                                        fontSize: 20
                                    }
                                },
                                data: data.dataList
                                /*
                                    [
                                        {value: 60, name: '01资质审查'},
                                        {value: 40, name: '咨询'},
                                        {value: 20, name: '订单'},
                                        {value: 80, name: '点击'},
                                        {value: 100, name: '展现'}
                                    ]
                                */
                            }
                        ]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            });
        }
    </script>
</head>
<body>
    <!--为 ECharts 准备一个具备大小（宽高）的DOM对象-->
    <div id="main" style="width: 600px;height: 400px;"></div>
</body>
</html>