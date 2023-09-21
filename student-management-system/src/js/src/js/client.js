import axios from 'axios';

const checkStatus = response => {
  if (response.status >= 200 && response.status < 300) {
    return response;
  } else {
    const error = new Error(response.statusText);
    error.response = response;

    if (response.data) {
      error.error = response.data;
    }

    return Promise.reject(error);
  }
};

const instance = axios.create({
  baseURL: 'http://localhost:9090'
});

export const getAllStudents = () => instance.get('/students').then(checkStatus);

export const addNewStudent = student => instance.post('/students', student);

export const deleteStudent = (studentID) => {
  return instance
    .delete(`/students/${studentID}`)
    .then(checkStatus)
    .catch(error => {
      console.error('Error deleting student:', error);
      throw new Error('Failed to delete student');
    });
};