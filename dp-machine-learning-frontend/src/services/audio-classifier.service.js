import * as axios from 'axios'

function predictAudio (formData) {
  const url = `./api/classifiers/audio`
  return axios.post(url, formData)
  // get data
    .then(x => x.data)
}

export { predictAudio }
