var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var routes = require('./routes/index');
var users = require('./routes/users');
var sign = require('./routes/sign');
var detailview = require('./routes/detailview');
var review = require('./routes/review');
var manufactor = require('./routes/manufactor');
var mypage = require('./routes/mypage');
var homeview = require('./routes/homeview');
var mrmilk = require('./routes/mrmilk');
var msmilk = require('./routes/msmilk');
var ingre_ranking = require('./routes/ingre_ranking');
var ingredientview = require('./routes/ingredientview');
var search = require('./routes/search');
var reviewgood = require('./routes/reviewgood');
var reviewbad = require('./routes/reviewbad');
var reviewview = require('./routes/reviewview');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

// uncomment after placing your favicon in /public
//app.use(favicon(path.join(__dirname, 'public', 'favicon.ico')));
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', routes);
app.use('/users', users);
app.use('/sign', sign);
app.use('/detailview', detailview);
app.use('/review', review);
app.use('/manufactor', manufactor);
app.use('/mypage', mypage);
app.use('/homeview', homeview);
app.use('/mrmilk', mrmilk);
app.use('/msmilk', msmilk);
app.use('/ingre_ranking', ingre_ranking);
app.use('/ingredientview', ingredientview);
app.use('/search', search);
app.use('/reviewgood', reviewgood);
app.use('/reviewbad', reviewbad);
app.use('/reviewview',reviewview);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  var err = new Error('Not Found');
  err.status = 404;
  next(err);
});

// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
  app.use(function(err, req, res, next) {
    res.status(err.status || 500);
    res.render('error', {
      message: err.message,
      error: err
    });
  });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
  res.status(err.status || 500);
  res.render('error', {
    message: err.message,
    error: {}
  });
});


module.exports = app;
