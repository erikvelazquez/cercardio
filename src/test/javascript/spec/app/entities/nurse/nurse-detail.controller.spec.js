'use strict';

describe('Controller Tests', function() {

    describe('Nurse Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockNurse, MockUserBD, MockCompany, MockGender;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockNurse = jasmine.createSpy('MockNurse');
            MockUserBD = jasmine.createSpy('MockUserBD');
            MockCompany = jasmine.createSpy('MockCompany');
            MockGender = jasmine.createSpy('MockGender');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Nurse': MockNurse,
                'UserBD': MockUserBD,
                'Company': MockCompany,
                'Gender': MockGender
            };
            createController = function() {
                $injector.get('$controller')("NurseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:nurseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
