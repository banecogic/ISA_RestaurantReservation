'use strict';

angular.module('rrappApp')
    .factory('Friendrequest', function ($resource, DateUtils) {
        return $resource('api/friendrequests/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.sentDateAndTime = DateUtils.convertDateTimeFromServer(data.sentDateAndTime);
                    data.acceptedDateAndTime = DateUtils.convertDateTimeFromServer(data.acceptedDateAndTime);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
