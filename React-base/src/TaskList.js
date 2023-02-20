import React from "react";
import Task from "./Task";

export default ({tasks, setTaskDone, deleteTask}) => {
    return (
        <ul className="list-group">
            {tasks.map(task => {
                return (
                    <li key={task.id} className="list-group-item">
                        {/*2. 使用<Task>组件*/}
                        <Task task={task} setTaskDone={setTaskDone} deleteTask={deleteTask}/>
                    </li>
                );
            })}
        </ul>
    );
}

