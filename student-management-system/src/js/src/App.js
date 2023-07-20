import React, { useEffect, useState } from 'react';
import { LoadingOutlined } from '@ant-design/icons';
import Container from './Container';
import './App.css';
import { getAllStudents } from './client';
import { Table, Avatar, Spin } from 'antd';

function App() {
  const [students, setStudents] = useState([]);
  const [isFetching, setIsFetching] = useState(false);

  useEffect(() => {
    fetchStudents();
  }, []);

  const getSpinIcon = () => <LoadingOutlined style={{ fontSize: 24 }} spin />;

  const fetchStudents = () => {
    setIsFetching(true);
    getAllStudents()
      .then(response => response.data)
      .then(data => {
        setStudents(data);
        setIsFetching(false);
        console.log(data);
      })
      .catch(error => {
        setIsFetching(false);
        console.error("Error in fetching students", error);
      })
  }

  const columns = [
    {
      title: "",
      key: "avatar",
      render: (text, student) => (
        <Avatar size='large'>
          {`${student.firstName.charAt(0).toUpperCase()}
            ${student.lastName.charAt(0).toUpperCase()}`}
        </Avatar>
      )
    },
    {
      title: "Student ID",
      dataIndex: "id",
      key: "id",
    },
    {
      title: "First Name",
      dataIndex: "firstName",
      key: "firstName"
    },
    {
      title: "Last Name",
      dataIndex: "lastName",
      key: "lastName"
    },
    {
      title: "Email",
      dataIndex: "email",
      key: "email"
    },
    {
      title: "Gender",
      dataIndex: "gender",
      key: "gender"
    }
  ];

  return (
    <div className="App">
      {isFetching ? (
         <Container>
         <Spin indicator={getSpinIcon()}></Spin>
       </Container>
      ) : students.length > 0 ? (
        <Container>
        <Table
          dataSource={students}
          columns={columns}
          pagination={false}
          rowKey="id"
          style={{ marginBottom: '1em' }}
        />
        </Container>
      ) : (
        <h1>No students found</h1>
      )}
    </div>
  );
}

export default App;
