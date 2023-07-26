import { Formik, Form, Field, ErrorMessage } from 'formik';
import { Button } from 'antd';
import * as Yup from 'yup';
import './Error.css';
import { addNewStudent } from '../client';

const initialValues = {
    firstName: '',
    lastName: '',
    email: '',
    gender: 'MALE'
}

const validationSchema = Yup.object({
    firstName: Yup.string().required('*Required'),
    lastName: Yup.string().required('*Required'),
    email: Yup.string().email('Invalid email format').required('*Required'),
});

const error = props => {return <div className='error'>{props.children}</div>};

const AddStudentForm = props =>
        (
            <Formik
                initialValues={initialValues}
                validationSchema={validationSchema}
                onSubmit={(student, {setSubmitting}) => {
                    addNewStudent(student).then(() => {
                        props.onSuccess();
                    })
                    .catch(error => {
                        props.onFailure(error);
                    })
                    .finally(() => {
                        setSubmitting(false);
                    });
                }}
            >
                {({ isSubmitting,
                    submitForm,
                    isValid,
                    touched }) => (
                    <Form>
                        <div className='form-control'>
                            <label htmlFor='firstName' style={{ marginTop: '1em' }}>First Name</label>
                            <Field
                                name='firstName'>
                            </Field>
                            <ErrorMessage name='firstName' component={error}></ErrorMessage>
                        </div>
                        <div className='form-control'>
                            <label htmlFor='lastName'>Last Name</label>
                            <Field
                                name='lastName'>
                            </Field>
                            <ErrorMessage name='lastName' component={error}></ErrorMessage>
                        </div>
                        <div className='form-control'>
                            <label htmlFor='email'>Email</label>
                            <Field
                                type='email'
                                name='email'
                                placeholder='johndoe@gmail.com'>
                            </Field>
                            <ErrorMessage name='email' component={error}></ErrorMessage>
                        </div>
                        <div className='form-control'>
                            <label htmlFor='gender'>Select gender</label>
                            <Field
                                component='select'
                                name='gender'>
                                    <option value='MALE'>Male</option>
                                    <option value='FEMALE'>Female</option>
                            </Field>
                        </div>
                        <Button
                            type='submit'
                            disabled={isSubmitting || (touched && !isValid)}
                            style={{ marginTop: '2em' }}
                            onClick={() => submitForm()}>
                            Submit
                        </Button>
                    </Form>
                )}
            </Formik>
        );

export default AddStudentForm;