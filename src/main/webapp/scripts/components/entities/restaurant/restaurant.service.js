'use strict';

angular.module('rrappApp')
    .factory('Restaurant', function ($resource, DateUtils) {
        return $resource('api/restaurants/:id', {}, {
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
