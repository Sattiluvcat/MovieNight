import axios from 'axios'

const API_URL = 'http://localhost:8080/user/movie'

export const searchMovies = (keyword) => {
    return axios.get(`${API_URL}/search`, { params: { keyword } })
}

export const searchByTags = (tags, languages) => {
    return axios.get(`${API_URL}/categorySearch`, {
        params: {
            tags: tags.join(','),
            languages: languages.join(',')
        }
    })
}