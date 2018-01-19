(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('Type_ProgramDetailController', Type_ProgramDetailController);

    Type_ProgramDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Type_Program', 'Programs'];

    function Type_ProgramDetailController($scope, $rootScope, $stateParams, previousState, entity, Type_Program, Programs) {
        var vm = this;

        vm.type_Program = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:type_ProgramUpdate', function(event, result) {
            vm.type_Program = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
