import fetch from 'dva/fetch';

function parseJSON(response) {
  return response.json();
}

function checkStatus(response) {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }

  const error = new Error(response.statusText);
  error.response = response;
  throw error;
}

/**
 * Requests a URL, returning a promise.
 *
 * @param  {string} url       The URL we want to request
 * @param  {object} [options] The options we want to pass to "fetch"
 * @return {object}           An object containing either "data" or "err"
 */
export default function request(url, options) {
  //url = url.replace(' ', '+');
  return fetch(url, options)
    .then(checkStatus)
    .then(parseJSON)
    .then(data => ({data}))
    .catch(err => ({ err }));
}
export function requestWithoutError(url, options) {
  return fetch(url, options)
    .then(parseJSON)
    .then(data => ({data}));
}


export function getToken(url, options) {
  return fetch(url, options)
    .then(res => res.headers.get('Authorization'))
    .then(str => {
      if (str != null) {
        return str.split(' ')[1];
      } else {
        return null;
      }
    })
    .catch(err => ({ err }));
}
