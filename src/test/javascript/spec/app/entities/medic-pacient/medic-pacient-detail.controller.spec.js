'use strict';

describe('Controller Tests', function() {

    describe('MedicPacient Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMedicPacient, MockMedic, MockPacient;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMedicPacient = jasmine.createSpy('MockMedicPacient');
            MockMedic = jasmine.createSpy('MockMedic');
            MockPacient = jasmine.createSpy('MockPacient');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MedicPacient': MockMedicPacient,
                'Medic': MockMedic,
                'Pacient': MockPacient
            };
            createController = function() {
                $injector.get('$controller')("MedicPacientDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:medicPacientUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
