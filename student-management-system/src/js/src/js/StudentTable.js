import React from "react";
import {Table, Avatar} from 'antd';

const StudentTable = ({students, columns}) => (
    <Table
        dataSource={students}
        columns={columns}
        pagination={false}
        rowKey="studentID"
        style={{marginTop: '2em'}}
    />
);

export default StudentTable;