import Vue from 'vue'
import Router from 'vue-router'
import MachineLearningCatalogue from '@/components/MachineLearningCatalogue'
import AudioClassifier from '@/components/AudioClassifier'
import AudioSearch from '@/components/AudioSearch'
import MusicRecommender from '@/components/MusicRecommender'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'MachineLearningCatalogue',
      component: MachineLearningCatalogue
    },
    {
      path: '/audio_classifier',
      name: 'AudioClassifier',
      component: AudioClassifier
    },
    {
      path: '/audio_search',
      name: 'AudioSearch',
      component: AudioSearch
    },
    {
      path: '/music_recommender',
      name: 'MusicRecommender',
      component: MusicRecommender
    }
  ]
})
