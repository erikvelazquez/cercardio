'use strict';

describe('Controller Tests', function() {

    describe('Type_Program Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockType_Program, MockPrograms;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockType_Program = jasmine.createSpy('MockType_Program');
            MockPrograms = jasmine.createSpy('MockPrograms');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Type_Program': MockType_Program,
                'Programs': MockPrograms
            };
            createController = function() {
                $injector.get('$controller')("Type_ProgramDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'cercardiobitiApp:type_ProgramUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
