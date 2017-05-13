import ReactDOM from 'react-dom';

import dva from 'dva';
import createLoading from 'dva-loading';
import { browserHistory, hashHistory } from 'dva/router';

import { LocaleProvider } from 'antd';
import enUS from 'antd/lib/locale-provider/en_US';

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

app.model(require("./models/movies"));

// 4. Router
app.router(require('./router'));

// 5. Start
const App  = app.start();

ReactDOM.render(
  <LocaleProvider locale={enUS}>
    <App />
  </LocaleProvider>,
  document.getElementById('root')
);
