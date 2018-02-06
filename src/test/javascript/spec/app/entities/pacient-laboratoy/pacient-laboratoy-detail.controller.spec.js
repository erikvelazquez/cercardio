'use strict';

describe('Controller Tests', function() {

    describe('PacientLaboratoy Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPacientLaboratoy, MockPacient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPacientLaboratoy = jasmine.createSpy('MockPacientLaboratoy');
            MockPacient = jasmine.createSpy('MockPacient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PacientLaboratoy': MockPacientLaboratoy,
                'Pacient': MockPacient
            };
            createController = function() {
                $injector.get('$controller')("PacientLaboratoyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:pacientLaboratoyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
