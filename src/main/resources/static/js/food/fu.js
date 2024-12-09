/**
 * @param {File} files
 * @returns {Promise<*>}
 */
async function uploadImages(...files) {
  const formData = new FormData();
  files.forEach(file => formData.append("files", file));
  return await axios({
    method: 'POST',
    url: '/u/i',
    data: formData,
    headers: {
      'Content-Type': 'multipart/form-data'
    }
  });
}

async function deleteFile(uuid, filename) {
  return axios.delete(`/i/${uuid}_${filename}`)
}

