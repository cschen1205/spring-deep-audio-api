<template>
  <div id="AudioClassifier">
    <div class="container">

      <!--UPLOAD-->
      <form enctype="multipart/form-data" novalidate v-if="isInitial || isSaving">
        <h1>Upload Audio File</h1>
        <div class="dropbox">
          <input type="file" :name="uploadFieldName" :disabled="isSaving"
                 @change="filesChange($event.target.files); fileCount = $event.target.files.length"
                 accept="audio/*" class="input-file">
          <p v-if="isInitial">
            Drag your file(s) here to begin<br> or click to browse
          </p>
          <p v-if="isSaving">
            Uploading {{ fileCount }} files...
          </p>
        </div>
      </form>
      <p v-if="isSuccess">
        {{ prediction }}
        <button @click="changeToInitial()">One More Time</button>
      </p>
    </div>
  </div>
</template>

<script>
import { predictAudio } from '../services/audio-classifier.service'

const STATUS_INITIAL = 0
const STATUS_SAVING = 1
const STATUS_SUCCESS = 2
const STATUS_FAILED = 3

export default {
  name: 'AudioClassifier',
  data () {
    return {
      prediction: {},
      uploadError: null,
      currentStatus: null,
      uploadFieldName: 'file'
    }
  },
  computed: {
    isInitial () {
      return this.currentStatus === STATUS_INITIAL
    },
    isSaving () {
      return this.currentStatus === STATUS_SAVING
    },
    isSuccess () {
      return this.currentStatus === STATUS_SUCCESS
    },
    isFailed () {
      return this.currentStatus === STATUS_FAILED
    },
    getPrediction () {
      return this.prediction
    }
  },
  methods: {
    reset () {
      // reset form to initial state
      this.currentStatus = STATUS_INITIAL
      this.uploadError = null
      console.log('reset')
    },
    save (formData) {
      // upload data to the server
      this.currentStatus = STATUS_SAVING

      predictAudio(formData)
        .then(x => {
          console.log(x)
          this.prediction = x
          this.currentStatus = STATUS_SUCCESS
        })
        .catch(err => {
          this.uploadError = err.response
          this.currentStatus = STATUS_FAILED
        })
    },
    changeToInitial () {
      this.currentStatus = STATUS_INITIAL
    },
    filesChange (fileList) {
      // handle file changes
      const formData = new FormData()

      if (!fileList.length) return

      formData.append('description', 'this is an image')
      formData.append('id', '0000')
      formData.append('filename', 'image.png')
      formData.append('file', fileList[0])

      // save it
      this.save(formData)
    }
  },
  mounted () {
    this.reset()
  }
}
</script>

<!-- SASS styling -->
<style lang="scss">
  .dropbox {
    outline: 2px dashed grey; /* the dash box */
    outline-offset: -10px;
    background: lightcyan;
    color: dimgray;
    padding: 10px 10px;
    min-height: 200px; /* minimum height */
    position: relative;
    cursor: pointer;
  }

  .input-file {
    opacity: 0; /* invisible but it's there! */
    width: 100%;
    height: 200px;
    position: absolute;
    cursor: pointer;
  }

  .dropbox:hover {
    background: lightblue; /* when mouse over to the drop zone, change color */
  }

  .dropbox p {
    font-size: 1.2em;
    text-align: center;
    padding: 50px 0;
  }
</style>
