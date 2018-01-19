(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .controller('TimeDialogController', TimeDialogController);

    TimeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Time'];

    function TimeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Time) {
        var vm = this;

        vm.time = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.time.id !== null) {
                Time.update(vm.time, onSaveSuccess, onSaveError);
            } else {
                Time.save(vm.time, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cercardiobitiApp:timeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
