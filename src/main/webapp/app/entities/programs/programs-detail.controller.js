(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('ProgramsDetailController', ProgramsDetailController);

    ProgramsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Programs'];

    function ProgramsDetailController($scope, $rootScope, $stateParams, previousState, entity, Programs) {
        var vm = this;

        vm.programs = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:programsUpdate', function(event, result) {
            vm.programs = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
