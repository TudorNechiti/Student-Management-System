import React from "react";
import {Modal} from "antd";
import AddStudentForm from "./Forms/AddStudentForm";

const AddStudentModal = ({open, onClose, onSuccess, onFailure}) => (
    <Modal 
        className='custom-modal'
        open={open}
        onOk={onClose}
        onCancel={onClose}
        width={500} 
    >
        <h2>New student</h2>
        <hr />
        <AddStudentForm onSuccess={onSuccess} onFailure={onFailure}/>
    </Modal>
);

export default AddStudentModal;