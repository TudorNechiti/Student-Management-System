import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';
import './Error.css';
import './CustomStyles.css';
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

const error = props => { return <div className='error'>{props.children}</div> };

const AddStudentForm = (props) =>
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
                                name='firstName'
                                className='field'>
                            </Field>
                            <ErrorMessage name='firstName' component={error}></ErrorMessage>
                        </div>
                        <div className='form-control'>
                            <label htmlFor='lastName'>Last Name</label>
                            <Field
                                name='lastName'
                                className='field'>
                            </Field>
                            <ErrorMessage name='lastName' component={error}></ErrorMessage>
                        </div>
                        <div className='form-control'>
                            <label htmlFor='email'>Email</label>
                            <Field
                                type='email'
                                name='email'
                                className='field'
                                placeholder='johndoe@gmail.com'>
                            </Field>
                            <ErrorMessage name='email' component={error}></ErrorMessage>
                        </div>
                        <div className='form-control'>
                            <label htmlFor='gender'>Select gender</label>
                            <Field
                                component='select'
                                name='gender'
                                className='field'>
                                    <option value='MALE'>Male</option>
                                    <option value='FEMALE'>Female</option>
                            </Field>
                        </div>
                        <button
                            type='submit'
                            className='submit-button'
                            disabled={isSubmitting || (touched && !isValid)}
                            style={{ marginTop: '2em' }}
                            onClick={() => submitForm()}>
                            Submit
                        </button>
                    </Form>
                )}
            </Formik>
        );

export default AddStudentForm;