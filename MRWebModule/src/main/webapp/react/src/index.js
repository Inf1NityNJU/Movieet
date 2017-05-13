import dva from 'dva';
import createLoading from 'dva-loading';


import { browserHistory, hashHistory } from 'dva/router';

import './index.css';

// 1. Initialize
const app = dva({
  history: hashHistory,
});

// 2. Plugins
// app.use({});
app.use(createLoading());

// 3. Model
// app.model(require('./models/example'));
app.model(require("./models/user"));

// 4. Router
app.router(require('./router'));

// 5. Start
app.start('#root');
