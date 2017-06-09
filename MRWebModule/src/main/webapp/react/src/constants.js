export const NEW_RELEASED_SIZE = 4;

export const RECOMMEND_SIZE = 4;

export const CATEGORY_SIZE = 8;

export const SEARCH_PREVIEW_MOVIE_SIZE = 4;

export const SEARCH_PREVIEW_DIRECTOR_SIZE = 4;

export const SEARCH_PREVIEW_ACTOR_SIZE = 4;

export const SEARCH_MOVIE_SIZE = 10;

export const SEARCH_DIRECTOR_SIZE = 8;

export const SEARCH_ACTOR_SIZE = 8;

export const LIKE_SIZE = 4;

export const PREVIEW_REVIEW_SIZE = 3;

export const REVIEW_SIZE = 10;

export const PREVIEW_COLLECT_SIZE = 4;

export const PREVIEW_EVALUATE_SIZE = 4;

export const COLLECT_SIZE = 8;

export const EVALUATE_SIZE = 8;

export const PREVIEW_FRIEND_SIZE = 4;

export const FRIEND_SIZE = 12;

export const PREDICTION_SEARCH_SIZE = 4;

export const RANK_MOVIE_SIZE = 50;

export const RANK_PEOPLE_SIZE = 15;

// export const GENRES = ['All',
//   'Action',
//   'Adventure',
//   'Animation',
//   'Biography',
//   'Comedy',
//   'Crime',
//   'Documentary',
//   'Drama',
//   'Family',
//   'Fantasy',
//   'History',
//   'Horror',
//   'Music',
//   'Musical',
//   'Mystery',
//   'Romance',
//   'SciFi',
//   'Short',
//   'Others'];
// export const GENRES = [0, 12,14,16,18,27,28,35,36,37,53,80,99,878,9648,10402,10749,10751,10752,10769,10770];
export const GENRES = [
    {
        id: 0,
        value: 'All',
    },
    {
        id: 12,
        value: 'Adventure',
    },
    {
        id: 14,
        value: 'Fantasy',
    },
    {
        id: 16,
        value: 'Animation',
    },
    {
        id: 18,
        value: 'Drama',
    },
    {
        id: 27,
        value: 'Horror',
    },
    {
        id: 28,
        value: 'Action',
    },
    {
        id: 35,
        value: 'Comedy',
    },
    {
        id: 36,
        value: 'History',
    },
    {
        id: 37,
        value: 'Western',
    },
    {
        id: 53,
        value: 'Thriller',
    },
    {
        id: 80,
        value: 'Crime',
    },
    {
        id: 99,
        value: 'Documentary',
    },
    {
        id: 878,
        value: 'Science Fiction',
    },
    {
        id: 9648,
        value: 'Mystery',
    },
    {
        id: 10402,
        value: 'Music',
    },
    {
        id: 10749,
        value: 'Romance',
    },
    {
        id: 10751,
        value: 'Family',
    },
    {
        id: 10752,
        value: 'War',
    },
    {
        id: 10769,
        value: 'Foreign',
    },
    {
        id: 10770,
        value: 'TV Movie',
    }
];

export const GENRES_MAP = {
    0: 'All',
    12: 'Adventure',
    14: 'Fantasy',
    16: 'Animation',
    18: 'Drama',
    27: 'Horror',
    28: 'Action',
    35: 'Comedy',
    36: 'History',
    37: 'Western',
    53: 'Thriller',
    80: 'Crime',
    99: 'Documentary',
    878: 'Science Fiction',
    9648: 'Mystery',
    10402: 'Music',
    10749: 'Romance',
    10751: 'Family',
    10752: 'War',
    10769: 'Foreign',
    10770: 'TV Movie',
};

export const COUNTRY = [
    {
        id: 2,
        value: 'USA'
    },
    {
        id: 11,
        value: 'Britain'
    },
    {
        id: 12,
        value: 'France'
    },
    {
        id: 3,
        value: 'Japan'
    },
    {
        id: 37,
        value: 'India'
    },

    {
        id: 22,
        value: 'China HongKong'
    },
    {
        id: 23,
        value: 'China'
    },
    {
        id: 25,
        value: 'Korea'
    },
];

export const MOVIE_SORT = ['score', 'date'];

export const ORDER = ['asc', 'desc'];

export const SEARCH_STATUS = ['All', 'Movie', 'Director', 'Actor'];

export const USER_MOVIE_STATUS = ['collect', 'evaluate'];

export const BOX_OFFICE_ARRAY = [0, 413929, 2851900.5, 7637468.5, 14387590.5, 24103045.5, 39008504.5, 63816897, 108075290.5, 210563855];

export const SCORE_FR_ARRAY = [0, 5.25, 5.75, 6.15, 6.45, 6.65, 6.85, 7.15, 7.45, 7.85];

export const SCORE_CN_ARRAY = [0, 5.95, 6.35, 6.65, 6.85, 7.15, 7.35, 7.65, 7.95, 8.35];

export const VOTE_FR_ARRAY = [0, 4101.5, 8766.5, 14620.5, 22783, 34234, 50995.5, 75645.5, 115401, 211708.5];

export const VOTE_CN_ARRAY = [0, 77.5, 222.5, 534.5, 1165, 2419.5, 4268.5, 8787.5, 18973.5, 51949.5];

export const DESCRIPTION_ARRAY = {
    '00000': 'Just a failure',
    '10000': 'Attract only few foreigners',
    '01000': 'Foreigners watch and dislike it',
    '00100': 'Only attract few people here',
    '00010': 'We watch and dislike it',
    '00001': 'Money reaper',
    '11000': 'A movie detonation overseas',
    '10100': 'Well received worldwide',
    '10010': 'Hot in China well praised overseas',
    '10001': 'Few Chinese know but good',
    '01100': 'Hot overseas well praised here',
    '01010': 'Being watched globally but bad',
    '01001': 'Hot but horrible for foreigners',
    '00110': 'A winning in China',
    '00101': 'Few foreigners know but good',
    '00011': 'Hot but horrible for us',
    '11100': 'Not popular in China',
    '11010': 'Set overseas movie world on fire',
    '11001': 'May fail in China',
    '10110': 'Not popular overseas',
    '10101': 'Praise plus revenue',
    '10011': 'Dark horse overseas',
    '01110': 'Set domestic movie world on fire',
    '01101': 'An unexpected winner here',
    '01011': 'Popularity plus revenue',
    '00111': 'A failure at other countries',
    '11110': 'Nearly success perhaps bankruptcy',
    '11101': 'Lack popularity in China',
    '11011': 'Not welcomed at home',
    '10111': 'Lack popularity beyond the sea',
    '01111': 'Not welcomed beyond the sea',
    '11111': 'Complete success'
};

export const ESTIMATE_TYPE = [
    {
        value: 'boxOffice',
        name: 'Box Office',
    },
    {
        value: 'scoreFR',
        name: 'Foreign Score',
    },
    {
        value: 'scoreCN',
        name: 'Domestic Score',
    },
    {
        value: 'votesFR',
        name: 'Foreign Votes',
    },
    {
        value: 'votesCN',
        name: 'Domestic Votes',
    }
];