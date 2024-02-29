async function uploadToServer(formObj) {
    console.log(formObj)

    const response = await axios({
        method: 'post',
        url: '/files',
        data: formObj,
        headers: {
            'Content-Type': 'multipart/form-data',
        }
    })

    return response.data
}

async function removeFileToServer(uuid, fileName) {

    const response = await axios.delete(`/files/${uuid}_${fileName}`)

    return response.data
}