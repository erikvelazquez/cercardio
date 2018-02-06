(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('AcademicDegreeDetailController', AcademicDegreeDetailController);

    AcademicDegreeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'AcademicDegree'];

    function AcademicDegreeDetailController($scope, $rootScope, $stateParams, previousState, entity, AcademicDegree) {
        var vm = this;

        vm.academicDegree = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:academicDegreeUpdate', function(event, result) {
            vm.academicDegree = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
