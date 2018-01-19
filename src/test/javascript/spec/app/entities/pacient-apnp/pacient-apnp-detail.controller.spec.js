'use strict';

describe('Controller Tests', function() {

    describe('PacientApnp Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPacientApnp, MockPacient, MockDrugAddiction, MockBackground, MockTime;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPacientApnp = jasmine.createSpy('MockPacientApnp');
            MockPacient = jasmine.createSpy('MockPacient');
            MockDrugAddiction = jasmine.createSpy('MockDrugAddiction');
            MockBackground = jasmine.createSpy('MockBackground');
            MockTime = jasmine.createSpy('MockTime');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PacientApnp': MockPacientApnp,
                'Pacient': MockPacient,
                'DrugAddiction': MockDrugAddiction,
                'Background': MockBackground,
                'Time': MockTime
            };
            createController = function() {
                $injector.get('$controller')("PacientApnpDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:pacientApnpUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
