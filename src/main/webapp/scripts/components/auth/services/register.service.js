'use strict';

angular.module('rrappApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


