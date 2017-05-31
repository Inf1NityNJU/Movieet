import React from 'react';
import { Router, Route, Redirect } from 'dva/router';

import Movies from "./routes/Movies";
import MovieDiscover from './routes/MovieDiscover';
import MovieCategory from "./routes/MovieCategory";
import MovieSearch from "./routes/MovieSearch";

import Movie from "./routes/Movie";
import MovieInfo from "./routes/MovieInfo";
import MovieReview from "./routes/MovieReview";

import User from "./routes/User";
import UserMovie from "./routes/UserMovie";
import UserFriend from "./routes/UserFriend";

import Analysis from "./routes/Analysis";


import Test from "./routes/Test.js";


function RouterConfig({ history }) {
  return (
    <Router history={history}>

      <Redirect from="/" to="/movies/discover"/>
      <Redirect from="/movies" to="/movies/discover"/>

      <Route path="/movies" component={Movies}>
        <Route path="/movies/discover" component={MovieDiscover}/>
        <Route path="/movies/category" component={MovieCategory}/>
        <Route path="/movies/search" component={MovieSearch}/>
      </Route>

      <Route path="/movie" component={Movie}>
        <Route path="/movie/:id" component={MovieInfo}/>
        <Route path="/movie/:id/review" component={MovieReview}/>
      </Route>

      <Redirect from="/user/:userId" to="/user/:userId/movie"/>
      <Route path="/user/:userId" component={User}>
        <Route path="/user/:userId/movie(/:status)" component={UserMovie}/>
        <Route path="/user/:userId/friend(/:status)" component={UserFriend} />
      </Route>
      <Route path="/analysis" component={Analysis} />

      <Route path="/test" component={Test} />
    </Router>
  );
}

export default RouterConfig;
