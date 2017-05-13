import React from 'react';
import { Router, Route, Redirect } from 'dva/router';
import IndexPage from './routes/IndexPage';

import Movie from "./routes/Movie.js";

function RouterConfig({ history }) {
  return (
    <Router history={history}>

      <Redirect from="/" to="/movies/discover" />
      <Route path="/movies/discover" component={IndexPage} />
      <Route path="/movie/:id" component={Movie} />
    </Router>
  );
}

export default RouterConfig;
