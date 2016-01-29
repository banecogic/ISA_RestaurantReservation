'use strict';

describe('Restaurant Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockRestaurant, MockSpot, MockMeal, MockClient;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRestaurant = jasmine.createSpy('MockRestaurant');
        MockSpot = jasmine.createSpy('MockSpot');
        MockMeal = jasmine.createSpy('MockMeal');
        MockClient = jasmine.createSpy('MockClient');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Restaurant': MockRestaurant,
            'Spot': MockSpot,
            'Meal': MockMeal,
            'Client': MockClient
        };
        createController = function() {
            $injector.get('$controller')("RestaurantDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'rrappApp:restaurantUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
