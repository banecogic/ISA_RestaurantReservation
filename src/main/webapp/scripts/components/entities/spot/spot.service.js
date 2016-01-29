'use strict';

angular.module('rrappApp')
    .factory('Spot', function ($resource, DateUtils) {
        return $resource('api/spots/:id', {}, {
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
