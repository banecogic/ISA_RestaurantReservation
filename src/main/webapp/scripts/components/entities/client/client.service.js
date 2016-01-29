'use strict';

angular.module('rrappApp')
    .factory('Client', function ($resource, DateUtils) {
        return $resource('api/clients/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.date_of_birth = DateUtils.convertLocaleDateFromServer(data.date_of_birth);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.date_of_birth = DateUtils.convertLocaleDateToServer(data.date_of_birth);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.date_of_birth = DateUtils.convertLocaleDateToServer(data.date_of_birth);
                    return angular.toJson(data);
                }
            }
        });
    });
