'use strict';

describe('Controller Tests', function() {

    describe('MedicNurse Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMedicNurse, MockMedic, MockNurse;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMedicNurse = jasmine.createSpy('MockMedicNurse');
            MockMedic = jasmine.createSpy('MockMedic');
            MockNurse = jasmine.createSpy('MockNurse');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MedicNurse': MockMedicNurse,
                'Medic': MockMedic,
                'Nurse': MockNurse
            };
            createController = function() {
                $injector.get('$controller')("MedicNurseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:medicNurseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
