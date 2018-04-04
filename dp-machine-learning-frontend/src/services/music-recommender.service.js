import * as axios from 'axios'

function recommendMusic (formData) {
  const url = `./api/recommend/music`
  return axios.post(url, formData)
  // get data
    .then(x => x.data)
}

export { recommendMusic }
