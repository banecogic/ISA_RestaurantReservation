'use strict';

angular.module('rrappApp')
    .service('FriendRequestSharedPropertyService', function () {
        var friendToRequest;

        return {
            getProperty: function () {
                return friendToRequest;
            },
            setProperty: function(value) {
                friendToRequest = value;
            }
        };
    });