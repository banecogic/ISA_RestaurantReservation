'use strict';

describe('Spot Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockSpot, MockRestaurant;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockSpot = jasmine.createSpy('MockSpot');
        MockRestaurant = jasmine.createSpy('MockRestaurant');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Spot': MockSpot,
            'Restaurant': MockRestaurant
        };
        createController = function() {
            $injector.get('$controller')("SpotDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'rrappApp:spotUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
