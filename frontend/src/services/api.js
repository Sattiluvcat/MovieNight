import axios from 'axios';
axios.defaults.withCredentials = true;

const API_URL = 'http://localhost:8080/user/movie';

export const searchMovies = (keyword) => {
    return axios.get(`${API_URL}/search`, { params: { keyword } });
}

export const searchByTags = (tags, languages) => {
    return axios.get(`${API_URL}/categorySearch`, {
        params: {
            tags: tags.join(','),
            languages: languages.join(',')
        }
    });
}

export const getMovieDetail = (_id) => {
    return axios.get(`${API_URL}/${_id}`);
}

export const watchTogether = (_id) => {
    return axios.post(`${API_URL}/${_id}/watch-together`);
}

export const sendComment = (_id, comment) => {
    return axios.post(`${API_URL}/${_id}/comment`, { comment });
}

export const getSuggestedMovies = () => {
    return axios.get(`${API_URL}/suggest`);
};