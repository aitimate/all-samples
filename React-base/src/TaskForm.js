import React from "react";
import ReactDOM from "react-dom";


export default ({creatTask}) => {
    function handleSubmit(e) {
        // 阻断事件冒泡，否则下一步将触发表单的`action`请求
        e.preventDefault();
        // 向上传递数据：前提是上级组件已经将函数传递到此
        let  desc = ReactDOM.findDOMNode(document.getElementById("newTask")).value
        if (desc){
            creatTask(desc)
        }
    }

    return (
        <form onSubmit={e=>handleSubmit(e)}>
            <h1>New Task</h1>
            <div className="mb-3">
                <label htmlFor="newTask" className="form-label">Task Description</label>
                <textarea className="form-control" id="newTask" />
            </div>
            <button type="submit" className="btn btn-primary mb-3">Submit</button>
        </form>
    );
}