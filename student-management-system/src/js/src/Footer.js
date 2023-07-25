import React from 'react';
import Container from './Container';
import { Button, Avatar } from 'antd';
import './Footer.css';

const Footer = props => (
    <div className='footer'>
        <Container>
            {props.numberOfStudents ?
             <Avatar 
             style={{backgroundColor: '#f56a00', marginRight:'0.5em', marginBottom:'0.6em'}}
             size='large' >{props.numberOfStudents}
             </Avatar> : null
             }
            <Button onClick={() => props.handleAddStudentClickEvent()} type='primary' style={{ marginTop: '1.1em' }}> Add new student +</Button>
        </Container>
    </div>
);

export default Footer;