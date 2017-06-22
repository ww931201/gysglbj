var YdxsList = React.createClass({
    getInitialState: function() {
       return {data:[]};
    },
    componentDidMount: function() {
        var ts = this;
        $.get("/ydxs/list", function(data){
            ts.setState({data: data});
        });
    },
    whlmTd: function(i, obj) {
        if(i==0){
            return "<td rowspan='"+obj.M_NUM+"'>"+obj.WHLM+"</td>"
        } 
        return "";
    },
    render: function () {
        return (
            <tbody>
                {this.state.data.map((obj, i) => {
                   return (
                        <tr key={i}>
                           {this.whlmTd(i, obj)}   <td>维护项目</td>   <td>维护内容</td>   <td>维护周期</td>   <td>计划执行月份</td> <td>作业部门</td>   <td>维护日期及详情</td>    <td>维护人</td>    <td>存在问题</td>   <td>采取措施</td>
                        </tr> 
                   );
                })}
            </tbody>
        );
    }
});

ReactDOM.render(<YdxsList/>,document.getElementById('tcbody'));