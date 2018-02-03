(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('GenderDetailController', GenderDetailController);

    GenderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Gender'];

    function GenderDetailController($scope, $rootScope, $stateParams, previousState, entity, Gender) {
        var vm = this;

        vm.gender = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:genderUpdate', function(event, result) {
            vm.gender = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
