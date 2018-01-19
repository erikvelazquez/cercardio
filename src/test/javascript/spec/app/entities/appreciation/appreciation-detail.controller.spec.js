'use strict';

describe('Controller Tests', function() {

    describe('Appreciation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAppreciation, MockPacient, MockMedic;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAppreciation = jasmine.createSpy('MockAppreciation');
            MockPacient = jasmine.createSpy('MockPacient');
            MockMedic = jasmine.createSpy('MockMedic');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Appreciation': MockAppreciation,
                'Pacient': MockPacient,
                'Medic': MockMedic
            };
            createController = function() {
                $injector.get('$controller')("AppreciationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:appreciationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
