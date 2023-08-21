import React, { useEffect, useState } from 'react';
import { LoadingOutlined } from '@ant-design/icons';
import Container from './Container';
import Footer from './Footer';
import './css/App.css'
import './Forms/CustomStyles.css';
import { getAllStudents } from './client';
import {Avatar, Spin, Empty } from 'antd';
import { errorNotification } from './Notification';
import StudentTable from './StudentTable';
import AddStudentModal from './AddStudentModal';

function App() {
  const [students, setStudents] = useState([]);
  const [isFetching, setIsFetching] = useState(false);
  const [modalVisibility, setModalVisibility] = useState(false);

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
        if (error.error && error.error.message) {
          errorNotification("Error in fetching students", error.error.message);
        } else {
          errorNotification("Error in fetching students", "An error occurred.");
        }
        console.error("Error in fetching students", error);
      });
  }

  const openAddStudentModal = () => setModalVisibility(true);

  const closeAddStudentModal = () => setModalVisibility(false);

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
      dataIndex: "studentID",
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
          <StudentTable
            students={students}
            columns={columns}
          />
          <AddStudentModal
            open={modalVisibility}
            onClose={closeAddStudentModal}
            onSuccess={() => {
              closeAddStudentModal();
              fetchStudents();
            }}
            onFailure={(error) => {
              console.log(JSON.stringify(error));
              errorNotification('Error', 'Cannot fetch students.');
            }}  
          />
          <Footer
            numberOfStudents={students.length}
            handleAddStudentClickEvent={openAddStudentModal}
          />
        </Container>
      ) : (
        <Container>
        <Empty 
          description={
            <h1>No students found</h1>
        }>
        </Empty>
        </Container>
      )}
    </div>
  );
}

export default App;
