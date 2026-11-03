import React, { useState } from 'react'
import { Link } from 'react-router-dom';
import instance from '../axiosConfig';

const Register = () => {
    const [formInputs,setFormInputs]=useState({
        'fullname':'',
        'email':'',
        'password':'',
        'confirmpassword':'',
        'state':'',
        'city':'',
        'usertype':''
    });

    const [errors,setErrors]=useState({
        'fullNameError':null,
        'emailError':null,
        'passwordError':null,
        'confirmPasswordError':null,
        'stateError':null,
        'cityError':null,
        'userTypeError':null
    });

    const [finalError,setFinalError]=useState(null);
    const [successMessage,setSuccessMessage]=useState(null);
    let error={

        }

    const formInputsHandler=(e)=>{
        const {name,value}=e.target;

        

        
        switch(name)
        {
            case 'fullname':
                if(value===null || value==='' || value.length<3)
                {
                    error.fullNameError='Name should be of atleast 3 characters!';
                }
                else if(parseInt(value))
                {
                    error.fullNameError='Please enter alphabets!'
                }
                else
                {
                    error.fullNameError=''
                }
                break;
            
            case 'email':
                if(value===null || value==='' || !value.match('^[a-zA-Z0-9]+@gmail\.[a-z]+$'))
                {
                    error.emailError='email should match pattern abc@gmail.com';
                }
                else
                {
                    error.emailError=''
                }
                break;
            
            case 'password':
                if(value===null || value==='' || value.length<5)
                {
                    error.passwordError='password should be of atleast 5 characters!';
                }
                else{
                    error.passwordError=''
                    
                }
                break;

            case 'confirmpassword':
                if(value===null || value==='' || value!==formInputs.password)
                {
                    error.confirmPasswordError='confirm password should match above password!';
                    
                }
                else
                {
                    error.confirmPasswordError='';
                }
                break;

            case 'state':
                if(value===null || value==='' || value.length<3)
                {
                        error.stateError='Please enter valid state name!';
                }
                else
                {
                    error.stateError='';
                }
                break;

            case 'city':
                if(value===null || value==='' )
                {
                    error.cityError="city can't be empty!";
                }
                else
                {
                    error.cityError='';
                }
                break;
            
            case 'usertype':
                if(value===null || value==='')
                {
                    error.userTypeError='Please select a user type!';
                }
                else
                {
                    error.userTypeError='';
                }
                break;
        }

       
        setErrors((prev)=>{return {...prev,...error}});
        setFormInputs((prev)=>{
            return {
            ...prev,
            [name]:value
            }
        })
    };

    const submitHandler=async (e)=>{
        e.preventDefault();

        if(errors.fullNameError!=='' || errors.emailError!=='' || errors.passwordError!=='' || errors.confirmPasswordError!=='' || errors.stateError!=='' || errors.cityError!=='' || errors.userTypeError!=='')
        {
             console.log(errors);
            setFinalError("Failed to Register!\n Please enter valid details!");
            setSuccessMessage(null);
        }
        else
        {
            console.log(error);

            setFinalError(null);
            try
            {
            await instance.post('/users/register',{
                'fullName':formInputs.fullname,
                'emailId':formInputs.email,
                'password':formInputs.password,
                'confirmPassword':formInputs.confirmpassword,
                'userState':formInputs.state,
                'userCity':formInputs.city,
                'userType':formInputs.usertype.toUpperCase()
            })
            setSuccessMessage("Registered Successfully!");
            error={};
            }
            catch(err)
            {
                if(err.status===409){
                    setFinalError('An User with this Email Id already exists! \nEither login with that OR register with new emailId!');
                }
                else
                {
                 setFinalError("Failed to Register!\n Please enter valid details!");
                }
                 setSuccessMessage(null);
            }
        }
        

    }
  return (
    <>
      <div className='homeUser-body' style={{height:'900px',marginTop:0}}>
        <h3 align='center'>User Register:</h3>
        <form className='form-body'>
            <div className="mb-3">
                <label for="exampleFormControlInput1" className="form-label">Full Name</label>
                <input type="text" className="form-control" id="exampleFormControlInput1" name='fullname' value={formInputs.fullname} onChange={formInputsHandler}/>
            </div>
            
                <div>{
                        errors.fullNameError?errors.fullNameError:null
                    }</div>
            
            <div className="mb-3">
                <label for="exampleFormControlInput2" className="form-label">Email Id</label>
                <input type="email" className="form-control" id="exampleFormControlInput2" name='email' value={formInputs.email} onChange={formInputsHandler}/>
            </div>
                <div >{
                        errors.emailError?errors.emailError:null
                    }</div>
            <div className="mb-3">
                <label for="exampleFormControlInput3" className="form-label">Password</label>
                <input type="password" className="form-control" id="exampleFormControlInput3" name='password' value={formInputs.password} onChange={formInputsHandler}/>
            </div>
                    <div>{
                        errors.passwordError?errors.passwordError:null
                    }</div>
            <div className="mb-3">
                <label for="exampleFormControlInput4" className="form-label">Confirm Password</label>
                <input type="password" className="form-control" id="exampleFormControlInput4" name='confirmpassword' value={formInputs.confirmpassword} onChange={formInputsHandler}/>
            </div>
                    <div>{
                        errors.confirmPasswordError?errors.confirmPasswordError:null
                    }</div>
            <div className="mb-3">
                <label for="exampleFormControlInput5" className="form-label">State</label>
                <input type="text" className="form-control" id="exampleFormControlInput5" name='state' value={formInputs.state} onChange={formInputsHandler}/>
            </div>
                    <div>{
                        errors.stateError?errors.stateError:null
                    }</div>
            <div className="mb-3">
                <label for="exampleFormControlInput6" className="form-label">City</label>
                <input type="text" className="form-control" id="exampleFormControlInput6" name='city' value={formInputs.city} onChange={formInputsHandler}/>
            </div>
                    <div>{
                        errors.cityError?errors.cityError:null
                    }</div>
            <div className="mb-3">
                <label>Type of User:</label>
               <select name='usertype' onChange={formInputsHandler}>
                    <option value=''>Select</option>
                    <option value='user'>User</option>
                    <option value='admin'>Admin</option>
               </select>
            </div>
                <div>{
                        errors.userTypeError?errors.userTypeError:null
                    }</div>
                <div style={{width:"70%",height:'50px',margin:"10% auto", display:'flex',justifyContent:'space-evenly'}}>
                    <button type='submit' className=' btn btn-primary' style={{width:'100%'}} onClick={submitHandler}>Register</button>
                     <h6 align='right' style={{marginTop:'0%'}}>Already a user?<Link to='/login'>Login</Link></h6>
                </div>
                    
        </form>
        {
            finalError?<h3 align='center' style={{whiteSpace:'pre-line'}}>{finalError}</h3>:null
        }
        {
            successMessage?<h3 align='center'>{successMessage}</h3>:null
        }
      </div>
    </>
  )
}

export default Register