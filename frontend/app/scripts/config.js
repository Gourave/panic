'use strict';

angular.module('config', [])

.constant('ENV', {
  'name': 'development',
  // Change this variable depending on what the server is
  'apiEndpoint': 'http://localhost:9000/'
});