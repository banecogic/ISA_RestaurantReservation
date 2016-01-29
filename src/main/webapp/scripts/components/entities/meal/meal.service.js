'use strict';

angular.module('rrappApp')
    .factory('Meal', function ($resource, DateUtils) {
        return $resource('api/meals/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
