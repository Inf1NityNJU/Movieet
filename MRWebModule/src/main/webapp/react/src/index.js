import ReactDOM from 'react-dom';

import dva from 'dva';
import createLoading from 'dva-loading';
import { browserHistory, hashHistory } from 'dva/router';

import { LocaleProvider } from 'antd';
import enUS from 'antd/lib/locale-provider/en_US';

import './index.css';

import G2 from 'g2';
import theme from './utils/theme';

var Global = G2.Global;
Global.setTheme(theme);

// 1. Initialize
const app = dva({
  history: hashHistory,
});

// 2. Plugins
// app.use({});
app.use(createLoading({
  effects: true,
}));

// 3. Model
// app.model(require('./models/example'));
app.model(require("./models/user"));
app.model(require("./models/people"));
app.model(require("./models/analysis"));
app.model(require("./models/movie"));
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


//
//import G2 from 'g2';
//
////Global.setTheme(theme);
//G2.Global.setTheme('dark');
