(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimesDetailController', TimesDetailController);

    TimesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Times'];

    function TimesDetailController($scope, $rootScope, $stateParams, previousState, entity, Times) {
        var vm = this;

        vm.times = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cercardiobitiApp:timesUpdate', function(event, result) {
            vm.times = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
