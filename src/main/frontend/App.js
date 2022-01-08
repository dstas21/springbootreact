import React, {useEffect, useState, useCallback} from "react";
import axios from "axios";
import {useDropzone} from 'react-dropzone';
import "./App.css";


const UserProfiles = () => {

    const [userProfiles, setUserProfiles] = useState([]);

    const fetchUserProfilels = () => {
        axios.get("http://localhost:8080/api/user-profile").then(res => {
            console.log(res);
            setUserProfiles(res.data)
        });
    }

    useEffect(() => {
        fetchUserProfilels();
    }, []);

    return userProfiles.map((userProfile, index) => {
        return (
            <div key={index}>
                {userProfile.uuid ? (
                    <img
                        src={`http://localhost:8080/api/user-profile/${userProfile.uuid}/image/download`}
                    />
                ) : null
                }
                <br/>
                <h1>{userProfile.userName}</h1>
                <p>{userProfile.uuid}</p>
                <Dropzone {...userProfile}/>
                <br/>
            </div>
        );
    });
}

function Dropzone({userUuid}) {
    const onDrop = useCallback(acceptedFiles => {
        const file = acceptedFiles[0];

        console.log(file);

        const formData = new FormData();
        formData.append("file", file)

        axios.post(`http://localhost:8080/api/user-profile/${userUuid}/image/upload`,
            formData,
            {
                headers: {
                    "Content-type": "multipart/form-data"
                }
            }
        ).then(() => {
            console.log("file uploaded successfully")
        }).catch(err => {
            console.log(err)
        });

    }, [])
    const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

    return (
        <div {...getRootProps()}>
            <input {...getInputProps()} />
            {
                isDragActive ?
                    <p>Drop the image here ...</p> :
                    <p>Drag 'n' drop user image, or click to select user image</p>
            }
        </div>
    )
}

function App() {
    return <div>
        <UserProfiles/>
    </div>
}