import React from "react";

export default ({task, setTaskDone, deleteTask}) => {
    // JSX：既能引用组件和变量，又能粘贴HTML代码(粘贴时元素的`class`属性名会自动改写为`className`，防解析冲突)
    return (
        // `<></>`作用：确保仅返回一个元素
        <>
            {/* 1. 已完成的任务有删除线，并且取消完成按钮 */}
            {task.done ? <del>{task.description}</del> : task.description}<br/>
            {/* 2. 未完成的任务有完成按，向上传递数据：前提是上级组件已经将函数传递到此 */}
            {task.done ? null :
                <button type="button" className="btn btn-success" onClick={()=>setTaskDone(task.id)}>
                    <svg className="bi bi-check" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg" >
                        <path fillRule="evenodd" d="M13.854 3.646a.5.5 0 010 .708l-7 7a.5.5 0 01-.708 0l-3.5-3.5a.5.5 0 11.708-.708L6.5 10.293l6.646-6.647a.5.5 0 01.708 0z" clipRule="evenodd" />
                    </svg>
                </button>
            }
            {/* 3. 删除任务按钮，向上传递数据：前提是上级组件已经将函数传递到此 */}
            <button type="button" className="btn btn-danger" onClick={()=>deleteTask(task.id)}>
                <svg className="bi bi-x" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                    <path fillRule="evenodd" d="M11.854 4.146a.5.5 0 010 .708l-7 7a.5.5 0 01-.708-.708l7-7a.5.5 0 01.708 0z" clipRule="evenodd"/>
                    <path fillRule="evenodd" d="M4.146 4.146a.5.5 0 000 .708l7 7a.5.5 0 00.708-.708l-7-7a.5.5 0 00-.708 0z" clipRule="evenodd"/>
                </svg>
            </button>
        </>
    );
}

